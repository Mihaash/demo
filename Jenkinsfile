pipeline {

    agent none

    tools {
        maven 'Maven 3.9.12'
    }

    stages {

        stage('Build') {
            agent any
            steps {
                script {
                    docker.image('maven:3.9.6-eclipse-temurin-17-alpine').inside {
                        echo 'Compiling application...'
                        sh 'mvn -B -Dmaven.test.skip=true compile'
                    }
                }
            }
        }

        stage('Test') {
            agent any
            steps {
                script {
                    docker.image('maven:3.9.6-eclipse-temurin-17-alpine').inside {
                        echo 'Running tests...'
                        sh 'mvn -B test'
                    }
                }
            }
        }

        stage('Package') {
            when { branch 'main' }

            parallel {

                stage('Maven Package') {
                    agent any
                    steps {
                        script {
                            docker.image('maven:3.9.6-eclipse-temurin-17-alpine').inside {

                                echo 'Packaging application...'

                                sh '''
                                GIT_SHORT_COMMIT=$(echo $GIT_COMMIT | cut -c1-7)
                                mvn versions:set -DnewVersion="$GIT_SHORT_COMMIT"
                                mvn versions:commit
                                mvn package -DskipTests
                                '''

                                archiveArtifacts '**/target/*.jar'
                            }
                        }
                    }
                }

                stage('Docker Build & Push') {
                    agent any
                    steps {
                        script {
                            docker.withRegistry('https://index.docker.io/v1/', 'dockerlogin') {
                                def commitHash = env.GIT_COMMIT.take(7)
                                def dockerImage = docker.build("initcron/sysfoo:${commitHash}", ".")

                                dockerImage.push()
                                dockerImage.push("latest")
                                dockerImage.push("dev")
                            }
                        }
                    }
                }

            }
        }
    }

    post {
        always {
            echo "Pipeline Completed."
        }
    }
}
