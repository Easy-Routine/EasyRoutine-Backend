pipeline {
    agent any

    environment {
        REPO_NAME = "EasyRoutine-Backend"
        CONTAINER_NAME = "EasyRoutine-Backend-Container"
    }

    stages {
        stage('Checkout') {
            steps {
                git url: 'https://github.com/Easy-Routine/EasyRoutine-Backend.git', branch: 'main'
            }
        }

        stage('Set Image Tag (by timestamp)') {
            steps {
                script {
                    def timestamp = new Date().format("yyyyMMdd-HHmm", TimeZone.getTimeZone("Asia/Seoul"))
                    IMAGE_TAG = "${REPO_NAME}:${timestamp}"
                    env.IMAGE_TAG = IMAGE_TAG // 공유되도록 설정
                    echo "Docker image tag: ${IMAGE_TAG}"
                }
            }
        }

        stage('Build Docker Image') {
            steps {
                sh "docker build -t ${IMAGE_TAG} ."
            }
        }

        stage('Run Docker Container') {
            steps {
                sh """
                docker stop ${CONTAINER_NAME} || true
                docker rm ${CONTAINER_NAME} || true
                docker run -d --name ${CONTAINER_NAME} -p 8080:8080 ${IMAGE_TAG}
                """
            }
        }
    }
}