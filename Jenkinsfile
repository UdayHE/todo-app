pipeline {
    agent any

    environment {
        IMAGE_NAME = "todo-app"
        CONTAINER_REGISTRY = "uhegde"
    }

    stages {
        stage('Clone Repository') {
            steps {
                git branch: 'master', url: 'https://github.com/UdayHE/todo-app.git'
            }
        }

        stage('Build & Test with Gradle') {
            steps {
                sh './gradlew clean assemble'
            }
        }

        stage('Build Docker Image') {
            steps {
                sh 'docker build -t $CONTAINER_REGISTRY/$IMAGE_NAME:latest .'
            }
        }

        stage('Push Docker Image') {
            steps {
                withDockerRegistry([credentialsId: 'docker-hub-credentials', url: 'https://index.docker.io/v1/']) {
                    sh 'docker push $CONTAINER_REGISTRY/$IMAGE_NAME:latest'
                }
            }
        }

        stage('Deploy to Local Docker') {
            steps {
                sh 'docker stop $IMAGE_NAME || true'
                sh 'docker rm $IMAGE_NAME || true'
                sh 'docker run -d --name $IMAGE_NAME -p 8080:8080 $CONTAINER_REGISTRY/$IMAGE_NAME:latest'
            }
        }
    }
}