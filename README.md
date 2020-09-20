# MSA demo project (ARC-015)
Default login/password: user/pass
Homework-1:
1. Add HelloController
2. Transform to multi module project: 
    platform-common, easy-wallet 
    https://spring.io/guides/gs/multi-module/
    https://github.com/spring-guides/gs-multi-module
3. Create UserService in platform-common, use in HelloController
Homework-2:
4. Build a docker image https://spring.io/guides/gs/spring-boot-docker/
	- ./gradlew bootBuildImage
	 
	    //created image easy-wallet for Docker
	- docker image ls
	
	    //easy-wallet                        0.0.1-SNAPSHOT          26879d13f184        40 years ago        284MB

	- docker image inspect docker.io/library/easy-wallet:0.0.1-SNAPSHOT
	
	- docker run -p 8080:8080 -t docker.io/library/easy-wallet:0.0.1-SNAPSHOT
	
	    //start docker image
	- http://localhost:8080/actuator/health
	    
	    //Result: {"status":"UP"}
	- docker ps
	
    	//fc791b840738        easy-wallet:0.0.1-SNAPSHOT   "/cnb/lifecycle/laun…"   3 minutes ago       Up 3 minutes        0.0.0.0:8080->8080/tcp   mystifying_torvalds

	- docker stop mystifying_torvalds
	
5.Deploy to kubernetes https://minikube.sigs.k8s.io/docs/handbook/pushing/     
https://kubernetes.io/docs/tutorials/hello-minikube/

  	- open terminal at project source root
  	- eval $(minikube docker-env)
  	- docker image ls (no image within kubernetes's docker)
  	- ./gradlew bootBuildImage
  	
  	//created image easy-wallet for K8S
  	
  	- docker image ls (have image!)
  	- kubectl create deployment easy-wallet --image=docker.io/library/easy-wallet:0.0.1-SNAPSHOT
  	- kubectl get deployments
  	- kubectl get events
  	- kubectl get pods
  	- kubectl expose deployment easy-wallet --type=LoadBalancer --port=8080
  	- minikube service easy-wallet
  	
  	//created service
  	- get Spring Boot password from dashboard pod log
  	
  	
  	- http://172.17.0.2:30674/actuator
  	
  	//Result: {"_links":{"self":{"href":"http://172.17.0.2:32048/actuator","templated":false},"health":{"href":"http://172.17.0.2:32048/actuator/health","templated":false},"health-path":{"href":"http://172.17.0.2:32048/actuator/health/{*path}","templated":true},"info":{"href":"http://172.17.0.2:32048/actuator/info","templated":false}}}
  	
6.Running multiple application instances

	- From the Dashboard -> Deployments -> Scale -> 2
	    kubectl scale -n default deployment easy-wallet --replicas=2
	    //created
	- minikube service easy-wallet
	- http://172.17.0.2:30674/actuator
	- Disable spring security so that not to guess password from which node
	- Build the image
	./gradlew bootBuildImage
	- Update the image: kubectl set image deployment/easy-wallet easy-wallet=docker.io/library/easy-wallet:0.0.1-SNAPSHOT
	- kubectl rollout restart deployment/easy-wallet
	- kubectl rollout status deployment/easy-wallet

RESULT:

Discovery and Load Balancing: -> Services:
    easy-wallet 
    (kubectl apply -f <spec.yaml>)


    kind: Service
    apiVersion: v1
    metadata:
      name: easy-wallet
      namespace: default
      selfLink: /api/v1/namespaces/default/services/easy-wallet
      uid: c04ece83-6712-422c-a085-cc1dda3560c3
      resourceVersion: '4637'
      creationTimestamp: '2020-09-20T15:55:07Z'
      labels:
        app: easy-wallet
      managedFields:
        - manager: kubectl
          operation: Update
          apiVersion: v1
          time: '2020-09-20T15:55:07Z'
          fieldsType: FieldsV1
          fieldsV1:
            'f:metadata':
              'f:labels':
                .: {}
                'f:app': {}
            'f:spec':
              'f:externalTrafficPolicy': {}
              'f:ports':
                .: {}
                'k:{"port":8080,"protocol":"TCP"}':
                  .: {}
                  'f:port': {}
                  'f:protocol': {}
                  'f:targetPort': {}
              'f:selector':
                .: {}
                'f:app': {}
              'f:sessionAffinity': {}
              'f:type': {}
    spec:
      ports:
        - protocol: TCP
          port: 8080
          targetPort: 8080
          nodePort: 32048
      selector:
        app: easy-wallet
      clusterIP: 10.102.97.201
      type: LoadBalancer
      sessionAffinity: None
      externalTrafficPolicy: Cluster
    status:
      loadBalancer: {}
