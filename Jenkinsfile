pipeline {
    agent any

    stages {
        stage('one') {
            steps {
                echo 'step 1'
            }
        }

        stage('two') {
            steps {
                echo 'step 2'
                sleep 9
            }
        }

        stage('three') {
            steps {
                echo 'step 3'
                sleep 5
            }
        }
    }

    post {
        always {
            echo 'This pipeline is completed..'
        }
    }
}
