pipeline {
    agent any

    environment {
        GITHUB_REPO_URL = 'https://github.com/mAbhishek0/SPE-Calculator'

        DOCKER_IMAGE = 'xlv02/spe-calculator'

        DOCKER_CRED_ID = 'dockerhubcred'
    }

    stages {
        stage('Checkout') {
            steps {
                git branch: 'main', url: "${GITHUB_REPO_URL}"
            }
        }

        stage('Build & Test') {
            steps {
                sh 'mvn clean package'
            }
        }

        stage('Docker Build') {
            steps {
                sh "docker build -t ${DOCKER_IMAGE}:latest ."
            }
        }

        stage('Docker Push') {
            steps {
                withCredentials([usernamePassword(credentialsId: env.DOCKER_CRED_ID, passwordVariable: 'DOCKER_PASS', usernameVariable: 'DOCKER_USER')]) {
                    sh "echo \$DOCKER_PASS | docker login -u \$DOCKER_USER --password-stdin"
                    sh "docker push ${DOCKER_IMAGE}:latest"
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
            mail to: 'am6156322@gmail.com',
                 subject: "SUCCESS: SPE Calculator Pipeline #${env.BUILD_NUMBER}",
                 body: "The build and deployment were successful! You can check the Jenkins console for details."
        }
        failure {
            mail to: 'am6156322@gmail.com',
                 subject: "FAILURE: SPE Calculator Pipeline #${env.BUILD_NUMBER}",
                 body: "The pipeline failed. Please check the Jenkins logs to debug the issue."
        }
        always {
            // Cleans up the workspace after the build finishes
            cleanWs()
        }
    }
}