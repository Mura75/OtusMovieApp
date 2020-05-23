#!groovy
pipeline {
    agent {
        docker {
            image 'android-docker'
            customWorkspace "${JENKINS_HOME}/workspace/${JOB_NAME}/${BUILD_NUMBER}"
        }
    }
  triggers { pollSCM('H/15 * * * *') }

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