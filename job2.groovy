job('job2k8s'){
description('we made this job for deployment')
triggers {
        upstream('Code-Fetch', 'SUCCESS')
    }

steps {       
shell('''if ! kubectl get pvc | grep PVC
         then
          kubectl apply -f /root/.jenkins/workspace/Code-Fetch/PVC.yml
         fi
         if ls /root/.jenkins/workspace/Code-Fetch/ | grep .php
         then
          if kubectl get deployment | grep php
          then 
           PODS=$(kubectl get pods -l app=php -o jsonpath="{.items[*].metadata.name}")
           for i in $PODS
            do 
             kubectl cp /root/.jenkins/workspace/Code-Fetch/*.php $i:/var/www/html/
            done
          else
           kubectl  create -f /root/.jenkins/workspace/Code-Fetch/deployment.yml
           sleep 25
           PODS=$(kubectl get pods -l app=php -o jsonpath="{.items[*].metadata.name}")
           for i in $PODS
            do 
             kubectl cp /root/.jenkins/workspace/Code-Fetch/*.php $i:/var/www/html/
            done
          fi 
         fi  
         if ! kubectl get svc | grep Service
         then
          kubectl create -f /root/.jenkins/workspace/Code-Fetch/Service.yml
         fi''') 
}
} 
