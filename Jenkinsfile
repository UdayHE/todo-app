pipeline {
    agent any

    environment {
        IMAGE_NAME = "todo-app"
        CONTAINER_REGISTRY = "uhegde"
        GRADLE_USER_HOME = "${WORKSPACE}/.gradle"  // Cache Gradle dependencies
        POSTGRES_CONTAINER = "todo-db"
        POSTGRES_USER = "postgres"
        POSTGRES_PASSWORD = "12345678"
        POSTGRES_DB = "todos"
        POSTGRES_PORT = "5432"
    }

    stages {
        stage('Clone Repository') {
            steps {
                git branch: 'master', url: 'https://github.com/UdayHE/todo-app.git'
            }
        }

        stage('Start PostgreSQL Container') {
            steps {
                script {
                    sh '''
                    docker stop ${POSTGRES_CONTAINER} || true
                    docker rm ${POSTGRES_CONTAINER} || true
                    docker run -d --name ${POSTGRES_CONTAINER} \
                    -e POSTGRES_USER=${POSTGRES_USER} \
                    -e POSTGRES_PASSWORD=${POSTGRES_PASSWORD} \
                    -e POSTGRES_DB=${POSTGRES_DB} \
                    -p ${POSTGRES_PORT}:5432 postgres:latest
                    '''
                    sleep(10)  // Wait for DB to initialize
                }
            }
        }

        stage('Verify PostgreSQL Connection') {
            steps {
                script {
                    def status = sh(script: """
                        docker exec ${POSTGRES_CONTAINER} pg_isready -U ${POSTGRES_USER}
                    """, returnStatus: true)

                    if (status != 0) {
                        error("PostgreSQL is not ready. Ensure Docker is running and retry.")
                    }
                }
            }
        }

        stage('Build & Test with Gradle') {
            steps {
                sh './gradlew clean assemble'
            }
        }

        stage('Build Docker Image') {
            steps {
                sh 'docker build --build-arg JAR_FILE=build/libs/*.jar -t $CONTAINER_REGISTRY/$IMAGE_NAME:latest .'
            }
        }

        stage('Push Docker Image') {
            steps {
                withDockerRegistry([credentialsId: 'docker-hub-credentials', url: 'https://index.docker.io/v1/']) {
                    sh 'docker push $CONTAINER_REGISTRY/$IMAGE_NAME:latest'
                }
            }
        }

        stage('Deploy `todo-app` with PostgreSQL on Docker') {
            steps {
                script {
                    sh '''
                    docker stop ${IMAGE_NAME} || true
                    docker rm ${IMAGE_NAME} || true
                    docker run -d --name ${IMAGE_NAME} -p 8080:8080 \
                    --link ${POSTGRES_CONTAINER}:postgres \
                    -e SPRING_DATASOURCE_URL=jdbc:postgresql://postgres:5432/${POSTGRES_DB} \
                    -e SPRING_DATASOURCE_USERNAME=${POSTGRES_USER} \
                    -e SPRING_DATASOURCE_PASSWORD=${POSTGRES_PASSWORD} \
                    $CONTAINER_REGISTRY/$IMAGE_NAME:latest
                    '''
                }
            }
        }

        stage('Post-Deployment Health Check') {
            steps {
                script {
                    sleep(10)  // Wait for app to start
                    def status = sh(script: "curl -s -o /dev/null -w '%{http_code}' http://localhost:8080/actuator/health", returnStdout: true).trim()
                    if (status != '200') {
                        error("Deployment failed. Service is not healthy.")
                    }
                }
            }
        }
    }
}