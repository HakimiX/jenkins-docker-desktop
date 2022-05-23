# Jenkins on Kubernetes using Docker Desktop

### Setup 

1. Create a Docker context 
```shell
docker context create docker-jenkins \
  --default-stack-orchestrator=kubernetes \
  --kubernetes config-file=/Users/x/.kube/config \
  --docker host=unix:///var/run/docker.sock

> 'Successfully created context "docker-jenkins"'
```
> Make sure that `/Users/x/.kube/config` exists

2. Change to the new context 
```shell
docker context use docker-jenkins
```

3. Export the Kubernetes context
```shell
docker context export docker-jenkins --kubeconfig

> 'Written file "docker-jenkins.kubeconfig"'
```

4. Copy the content of `docker-jenkins.kubeconfig` and add it to 
the `~/.kube/config` file. 


### Deploy Kubernetes Manifests 

1. Create a Kubernetes namespace 
```shell
kubectl create namespace jenkins

> 'namespace/jenkins created'
```

2. Use the new namespace
```shell
kubens jenkins

> 'Active namespace is "jenkins"'
```

3. Apply the Kubernetes manifests 
```shell
kubectl apply -f ./kubernetes

> 'deployment.apps/jenkins created'
> 'persistentvolume/jenkins created'
> 'persistentvolumeclaim/jenkins-claim created'
> 'serviceaccount/jenkins created'
> 'clusterrole.rbac.authorization.k8s.io/jenkins created'
> 'rolebinding.rbac.authorization.k8s.io/jenkins created'
> 'service/jenkins created'
```
![](resources/images/all-resources.png)
