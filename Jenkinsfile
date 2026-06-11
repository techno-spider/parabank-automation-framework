pipeline {
    agent any

    parameters {
        choice(
            name: 'TEST_TYPE',
            choices: ['smoke', 'regression', 'api', 'ui', 'all'],
            description: 'Select test suite to run'
        )
    }

    stages {

        stage('Checkout') {
            steps {
                checkout scm
            }
        }

        stage('Run Tests') {
            steps {
                script {
                    def mvnCommand = 'mvn clean test'

                    switch(params.TEST_TYPE) {
                        case 'all':
                            bat mvnCommand
                            break
                        case 'smoke':
                        case 'regression':
                            bat "${mvnCommand} -Dcucumber.filter.tags=@${params.TEST_TYPE}"
                            break
                        case 'api':
                            bat "${mvnCommand} -Dcucumber.filter.tags=@api"
                            break
                        case 'ui':
                            bat "${mvnCommand} -Dcucumber.filter.tags=@ui"
                            break
                    }
                }
            }
        }

        stage('Generate Allure Report') {
            steps {
                script {
                    copyArtifacts(
                        projectName: env.JOB_NAME,
                        selector: specific("${env.BUILD_NUMBER.toInteger() - 1}"),
                        filter: 'allure-report/history/**',
                        optional: true,
                        target: 'allure-results'
                    )
                }
            }
        }

    }

    post {
        always {
            cleanWs()
        }
        success {
            echo 'Tests passed successfully!'
        }
        failure {
            echo 'Tests failed! Check Allure report for details.'
        }
    }
}