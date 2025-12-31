pipeline {
    agent any

    tools {
        maven 'Maven 3.9.12'
    }

    stages {

        stage('Build') {
            steps {
                echo "Compiling..."
                sh "mvn -B -DskipTests clean compile"
            }
        }

        stage('Test') {
            steps {
                echo "Running tests..."
                sh "mvn -B test"
            }
        }

        stage('Package & Docker') {
            when { branch 'main' }
            parallel {

                stage('Maven Package') {
                    steps {
                        echo "Packaging..."
                        sh "mvn -B -DskipTests package"
                        archiveArtifacts artifacts: 'target/*.jar', fingerprint: true
                    }
                }

                stage('Docker Build & Push') {
                    steps {
                        script {
                            def tag = env.GIT_COMMIT.take(7)

                            docker.withRegistry('https://index.docker.io/v1/', 'dockerlogin') {
                                def img = docker.build("initcron/sysfoo:${tag}")
                                img.push()
                                img.push("latest")
                            }
                        }
                    }
                }
            }
        }
    }

    post {
        always {
            echo "Pipeline Completed"
        }
    }
}
