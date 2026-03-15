pipeline {
    agent any

    environment {
        GITHUB_REPO_URL = 'https://github.com/mAbhishek0/SPE-Calculator'
        DOCKER_IMAGE    = 'xlv02/spe-calculator'
        DOCKER_CRED_ID  = 'dockerhubcred'
    }

    triggers {
        githubPush()
    }

    stages {
        stage('Checkout') {
            steps {
                git branch: 'optimise-pipeline', url: "${GITHUB_REPO_URL}"
            }
        }

        // Single stage — compile + test + copy deps to target/dependency/ in one shot
        // Parallel stages caused workspace conflicts and target/dependency/ was never created
        sstage('Build & Test') {
             steps {
                 sh 'mvn clean package'
                 sh 'ls -la target/'          // shows exactly what Maven produced
                 sh 'ls -la target/dependency/ || echo "dependency folder MISSING"'
                 junit 'target/surefire-reports/*.xml'
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

        stage('Cleanup') {
            steps {
                sh 'docker image prune -f'
            }
        }
    }

    post {
        success {
            emailext(
                to: 'am6156322@gmail.com',
                subject: "SUCCESS: SPE Calculator Pipeline #${env.BUILD_NUMBER}",
                body: "Build #${env.BUILD_NUMBER} deployed. Image: ${DOCKER_IMAGE}:${env.BUILD_NUMBER}"
            )
        }
        failure {
            emailext(
                to: 'am6156322@gmail.com',
                subject: "FAILURE: SPE Calculator Pipeline #${env.BUILD_NUMBER}",
                body: "Pipeline failed. Logs: ${env.BUILD_URL}"
            )
        }
        always {
            sh 'docker logout'
            cleanWs()
        }
    }
}