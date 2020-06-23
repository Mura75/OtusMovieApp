#!groovy
pipeline {
  agent any

  triggers { pollSCM('H/15 * * * *') }

  stages {
      stage('prepare') {
          steps {
              sh "chmod +x gradlew"
              loadKeystore()
          }
      }
      stage('lint-check') {
          steps {
            sh "./gradlew lint"
          }
      }
      stage('build') {
          steps {
              sh "./gradlew clean assembleDebug --stacktrace"
          }
      }
      stage('buildRelease') {
            steps {
                sh "./gradlew clean assembleRelease --stacktrace"
            }
      }
      stage('test') {
          steps {
              sh "./gradlew test"
          }
      }
  }
}

  def loadKeystore() {
    def keystorePath = "./app"
    withCredentials(
        [
            file(credentialsId: 'keystore', variable: 'keystore'),
            file(credentialsId: 'properties', variable: 'keystore_properties')
        ]
    ) {
        sh "cp -f $keystore $keystorePath/upload-keystore.jks"
        sh "cp -f $keystore_properties $keystorePath/signing.properties"
    }
  }