pipeline {
    agent any

    environment {
        REPO_NAME = "easyroutine-backend"
        CONTAINER_NAME = "easyroutine-backend-container"

        JWT_SECRET_KEY = credentials('JWT_SECRET_KEY')
        GOOGLE_CLIENT_ID = credentials('GOOGLE_CLIENT_ID')
        GOOGLE_CLIENT_SECRET = credentials('GOOGLE_CLIENT_SECRET')
        KAKAO_CLIENT_ID = credentials('KAKAO_CLIENT_ID')
        KAKAO_CLIENT_SECRET = credentials('KAKAO_CLIENT_SECRET')
        NAVER_CLIENT_ID = credentials('NAVER_CLIENT_ID')
        NAVER_CLIENT_SECRET = credentials('NAVER_CLIENT_SECRET')
        AWS_ACCESS_KEY = credentials('AWS_ACCESS_KEY')
        AWS_REGION = credentials('AWS_REGION')
        AWS_S3_BUCKET = credentials('AWS_S3_BUCKET')
        AWS_S3_DIRECTORY = credentials('AWS_S3_DIRECTORY')
        AWS_SECRET_KEY = credentials('AWS_SECRET_KEY')
        DB_URL = credentials('DB_URL')
        DB_USER = credentials('DB_USER')
        DB_PASSWORD = credentials('DB_PASSWORD')
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
                docker run  -e JWT_SECRET_KEY=${JWT_SECRET_KEY} \\
                                       -e GOOGLE_CLIENT_ID=${GOOGLE_CLIENT_ID} \\
                                       -e GOOGLE_CLIENT_SECRET=${GOOGLE_CLIENT_SECRET} \\
                                       -e KAKAO_CLIENT_ID=${KAKAO_CLIENT_ID} \\
                                       -e KAKAO_CLIENT_SECRET=${KAKAO_CLIENT_SECRET} \\
                                       -e NAVER_CLIENT_ID=${NAVER_CLIENT_ID} \\
                                       -e AWS_ACCESS_KEY=${AWS_ACCESS_KEY} \\
                                       -e AWS_REGION=${AWS_REGION} \\
                                       -e AWS_S3_BUCKET=${AWS_S3_BUCKET} \\
                                       -e AWS_S3_DIRECTORY=${AWS_S3_DIRECTORY} \\
                                       -e AWS_SECRET_KEY=${AWS_SECRET_KEY} \\
                                       -e NAVER_CLIENT_SECRET=${NAVER_CLIENT_SECRET}  -d --name ${CONTAINER_NAME} -p 8080:8080 ${IMAGE_TAG}
                """
            }
        }
    }
}