job('job2k8s'){
description('we made this job for deployment')
triggers {
        upstream('Code-Fetch', 'SUCCESS')
    }

steps {
shell('''sudo cd /root/task6/
         sudo cp * -rvf /root/task6/
      ''')        
shell('''if ! kubectl get pvc | grep pvc
         then
          kubectl create -f /root/.jenkins/workspace/seed_job/pvc.yml
         fi
         if ls /root/.jenkins/workspace/seed_job/ | grep .php
         then
          if kubectl get deploy | grep php
          then 
           PODS=$(kubectl get pods -l app=php -o jsonpath="{.items[*].metadata.name}")
           for i in $PODS
            do 
             kubectl cp /root/.jenkins/workspace/seed_job/*.php $i:/var/www/html/
            done
          else
           kubectl  create -f /root/.jenkins/workspace/seed_job/deploy.yml
           sleep 25
           PODS=$(kubectl get pods -l app=php -o jsonpath="{.items[*].metadata.name}")
           for i in $PODS
            do 
             kubectl cp /root/.jenkins/workspace/seed_job/*.php $i:/var/www/html/
            done
          fi 
         fi  
         if ! kubectl get svc | grep svc
         then
          kubectl create -f /root/.jenkins/workspace/seed_job/svc.yml
         fi''') 
}
} 
