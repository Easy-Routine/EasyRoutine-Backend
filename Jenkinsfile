pipeline {
    agent any

    environment {
        REPO_NAME = "easyroutine-backend"
        CONTAINER_NAME = "easyroutine-backend-container"
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
                writeFile file: '.env', text: "${ENV_FILE}"
                sh """
                docker stop ${CONTAINER_NAME} || true
                docker rm ${CONTAINER_NAME} || true
                docker run  -e JWT_SECRET_KEY=${JWT_SECRET_KEY} \\
                                       -e GOOGLE_CLIENT_ID=${GOOGLE_CLIENT_ID} \\
                                       -e GOOGLE_CLIENT_SECRET=${GOOGLE_CLIENT_SECRET} \\
                                       -e KAKAO_CLIENT_ID=${KAKAO_CLIENT_ID} \\
                                       -e KAKAO_CLIENT_SECRET=${KAKAO_CLIENT_SECRET} \\
                                       -e NAVER_CLIENT_ID=${NAVER_CLIENT_ID} \\
                                       -e NAVER_CLIENT_SECRET=${NAVER_CLIENT_SECRET}  -d --name ${CONTAINER_NAME} -p 8080:8080 ${IMAGE_TAG}
                """
            }
        }
    }
}