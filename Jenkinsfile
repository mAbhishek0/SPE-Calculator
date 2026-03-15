pipeline {
    agent any

    environment {
        GITHUB_REPO_URL = 'https://github.com/mAbhishek0/SPE-Calculator'
        DOCKER_IMAGE    = 'xlv02/spe-calculator'
        DOCKER_CRED_ID  = 'dockerhubcred'
    }

    stages {
        stage('Checkout') {
            steps {
                git branch: 'main', url: "${GITHUB_REPO_URL}"
            }
        }

        stage('Build & Test') {
            steps {
                // Runs compile + test + copies deps to target/dependency/
                sh 'mvn clean package'
                // Publish test results in Jenkins UI
                junit 'target/surefire-reports/*.xml'
            }
        }

        stage('Docker Build') {
            steps {
                // Tag with both :latest and build number for rollback
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
    }

    post {
        success {
            // emailext is non-blocking unlike the basic mail step
            emailext(
                to: 'am6156322@gmail.com',
                subject: "SUCCESS: SPE Calculator Pipeline #${env.BUILD_NUMBER}",
                body: "Build and deployment successful. Image tag: ${env.BUILD_NUMBER}",
                async: true
            )
        }
        failure {
            emailext(
                to: 'am6156322@gmail.com',
                subject: "FAILURE: SPE Calculator Pipeline #${env.BUILD_NUMBER}",
                body: "Pipeline failed. Check logs at ${env.BUILD_URL}",
                async: true
            )
        }
        always {
            cleanWs()
        }
    }
}