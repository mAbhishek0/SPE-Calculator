pipeline {
    agent any

    environment {
        // Replace with your actual GitHub repository URL
        GITHUB_REPO_URL = 'https://github.com/mAbhishek0/SPE-Calculator'

        // Replace with your actual Docker Hub username
        DOCKER_IMAGE = 'xlv02/spe-calculator'

        // This MUST match the ID you create in Jenkins Credentials
        DOCKER_CRED_ID = 'dockerhubcred'
    }

    stages {
        stage('Checkout') {
            steps {
                // Ensure the branch name matches your repository (e.g., 'main' or 'master')
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
                // Updated to use the newly renamed deploy.yml file
                ansiblePlaybook(
                    playbook: 'deploy.yml',
                    inventory: 'inventory.ini'
                )
            }
        }
    }

    post {
        success {
            mail to: 'Abhishek.Mandal@iiitb.ac.in',
                 subject: "SUCCESS: SPE Calculator Pipeline #${env.BUILD_NUMBER}",
                 body: "The build and deployment were successful! You can check the Jenkins console for details."
        }
        failure {
            mail to: 'Abhishek.Mandal@iiitb.ac.in',
                 subject: "FAILURE: SPE Calculator Pipeline #${env.BUILD_NUMBER}",
                 body: "The pipeline failed. Please check the Jenkins logs to debug the issue."
        }
        always {
            // Cleans up the workspace after the build finishes
            cleanWs()
        }
    }
}