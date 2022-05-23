def PROJECTS = [
        // key is component name and value is git repo name
        'jenkins-docker-desktop': 'jenkins-docker-desktop',
        'component-one': 'component-one',
        'component-two': 'component-two'
]

def BRANCH = [
        'dev': 'dev',
        'test': 'test'
]

def ENV = [
        'dev': 'dev',
        'test': 'test'
]

// ---------------------------------------
// Pipelines
// ---------------------------------------

pipelineJob('deploy-applications') {
    parameters {
        choiceParam('PROJECTS', PROJECTS.keySet() as List)
        choiceParam('ENV', ['dev', 'test'])
        choiceParam('BRANCH', ['master', 'dev'])
    }
    definition {
        properties {
            disableConcurrentBuilds()
            buildDiscarder {
                strategy {
                    logRotator {
                        daysToKeepStr('100')
                        numToKeepStr('100')
                        artifactDaysToKeepStr('10')
                        artifactNumToKeepStr('10')
                    }
                }
            }
        }
        cpsScm {
            scm {
                git {
                    remote {
                        url('https://github.com/HakimiX/jenkins-docker-desktop') // The main infrastructure repo
                        credentials('GITHUB_ACCESS_TOKEN')
                    }
                    branch('master')
                }
            }
            scriptPath('Jenkinsfile.deploy')
            lightweight()
        }
    }
}



// ---------------------------------------
// Jenkins Tabs
// ---------------------------------------

listView('Deploy') {
    jobs {
        regex('.*deploy.*')
    }
    columns {
        status()
        weather()
        buildButton()
        name()
        lastSuccess()
        lastFailure()
    }
}
