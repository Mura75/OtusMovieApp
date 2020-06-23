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
      stage('publish') {
          environment {
              FIREBASE_APP_ID = credentials('FIREBASE_APP_ID')
              FIREBASE_TOKEN = credentials('FIREBASE_TOKEN')
          }
          steps {
              loadFirebasePublishJson()
              sh " ./gradlew assembleRelease appDistributionUploadRelease"
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
        sh "cp -f $keystore $keystorePath/keystore.jks"
        sh "cp -f $keystore_properties $keystorePath/signing.properties"
    }
  }


  def loadFirebasePublishJson() {
    def keystorePath = "./app"
    withCredentials(
        [
            file(credentialsId: 'publish_json', variable: 'google_app_credentials')
        ]
    ) {
            sh "cp -f $google_app_credentials $keystorePath/publish.json"
      }
  }