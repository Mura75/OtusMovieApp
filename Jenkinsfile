pipeline { 
    agent any 
    triggers { cron('H/5 * * * *') }      
      stages {  
           stage('prepare') { 
               steps { 
                   sh "chmod +x gradlew" 
               } 
           } 
           stage('build') { 
                  steps { 
                       sh "./gradlew clean assemble" 
                  } 
           } 
           stage('test') { 
                steps { 
                     sh "./gradlew test" 
                } 
           } 
      }
 }