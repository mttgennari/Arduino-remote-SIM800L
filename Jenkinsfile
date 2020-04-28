pipeline {
  agent any
  stages {
    stage('Build') {
      steps {
        sh 'arduino-cli compile --fqbn arduino:avr:uno Boiler.ino'
      }
    }

    stage('') {
      steps {
        echo 'Build Done'
      }
    }

  }
}