pipeline {
    agent any

    environment {
        GITHUB_REPO_URL = 'https://github.com/mAbhishek0/SPE-Calculator'
        DOCKER_IMAGE    = 'xlv02/spe-calculator'
        DOCKER_CRED_ID  = 'dockerhubcred'
    }

    // Only trigger pipeline on a push/PR to main
    // Prevents wasted builds on every commit to every branch
    triggers {
        githubPush()
    }

    stages {
        stage('Checkout') {
            steps {
                git branch: 'optimise-pipeline', url: "${GITHUB_REPO_URL}"
            }
        }

        stage('Compile') {
            steps {
                sh 'mvn clean compile'
            }
        }

        stage('Verify') {
            parallel {
                stage('Unit Tests') {
                    steps {
                        sh 'mvn test'
                        junit 'target/surefire-reports/*.xml'
                    }
                }
                stage('Package') {
                    steps {
                        sh 'mvn package -DskipTests'
                    }
                }
            }
        }

        stage('Docker Build') {
            steps {
                sh "docker build -t ${DOCKER_IMAGE}:latest -t ${DOCKER_IMAGE}:${env.BUILD_NUMBER} ."
            }
        }

        stage('Docker Push') {
            steps {
                withCredentials([usernamePassword(credentialsId: env.DOCKER_CRED_ID,
                                 passwordVariable: 'DOCKER_PASS',
                                 usernameVariable: 'DOCKER_USER')]) {
                    sh "echo \$DOCKER_PASS | docker login -u \$DOCKER_USER --password-stdin"
                    sh "docker push ${DOCKER_IMAGE}:latest"
                    sh "docker push ${DOCKER_IMAGE}:${env.BUILD_NUMBER}"
                }
            }
        }

        stage('Deploy with Ansible') {
            steps {
                ansiblePlaybook(
                    playbook: 'deploy.yml',
                    inventory: 'inventory.ini'
                )
            }
        }

        // Runs after deploy — removes dangling images to keep host disk clean
        // Keeps last 3 tagged images for rollback, prunes everything else
        stage('Cleanup') {
            steps {
                sh 'docker image prune -f'
                sh """
                    docker images ${DOCKER_IMAGE} --format '{{.Tag}}' | \
                    sort -rn | \
                    tail -n +4 | \
                    xargs -r -I {} docker rmi ${DOCKER_IMAGE}:{}
                """
            }
        }
    }

post {
    success {
        emailext(
            to: 'am6156322@gmail.com',
            subject: "SUCCESS: SPE Calculator Pipeline #${env.BUILD_NUMBER}",
            body: """
                Build #${env.BUILD_NUMBER} deployed successfully.
                Image: ${DOCKER_IMAGE}:${env.BUILD_NUMBER}
                To rollback: docker pull ${DOCKER_IMAGE}:<previous build number>
            """
        )
    }
    failure {
        emailext(
            to: 'am6156322@gmail.com',
            subject: "FAILURE: SPE Calculator Pipeline #${env.BUILD_NUMBER}",
            body: "Pipeline failed at build #${env.BUILD_NUMBER}. Check logs at ${env.BUILD_URL}"
        )
    }
    always {
        sh 'docker logout'
        cleanWs()
    }
}
}