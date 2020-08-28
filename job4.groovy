
job('job4notify'){
description(' job4 will send email to developer on failure ')
 
publishers {
        extendedEmail {
            recipientList('harvinderosahan.31@gmail.com')
            defaultSubject('site crashed!!')
            defaultContent('Please have a look at your code, Something went !Wrong!')
            contentType('text/html')
            triggers {
              always(){
          sendTo{
            recipientList()
                }
            }
        }
    }
}

}