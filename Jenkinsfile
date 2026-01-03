pipeline {
    agent any

    stages {
        stage('build') {
            steps {
                sh 'mvn complie'
            }
        }

        stage('test') {
            steps {
                echo 'step 2'
                sh 'mvn clean test '
            }
        }

        stage('package') {
            steps {
                echo 'step 3'
                sh 'mvn package -DskipTests'
            }
        }
    }

    post {
        always {
            echo 'This pipeline is completed..'
        }
    }
}
