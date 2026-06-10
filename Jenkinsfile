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
                            sh mvnCommand
                            break
                        case 'smoke':
                        case 'regression':
                            sh "${mvnCommand} -Dcucumber.filter.tags=@${params.TEST_TYPE}"
                            break
                        case 'api':
                            sh "${mvnCommand} -DsuiteXmlFile=src/test/resources/parallel-testng.xml"
                            break
                        case 'ui':
                            sh "${mvnCommand} -DsuiteXmlFile=src/test/resources/parallel-testng.xml"
                            break
                    }
                }
            }
        }

        stage('Generate Allure Report') {
            steps {
                script {
                    // Copy history from previous build
                    copyArtifacts(
                        projectName: env.JOB_NAME,
                        selector: specific("${env.BUILD_NUMBER.toInteger() - 1}"),
                        filter: 'allure-report/history/**',
                        optional: true,
                        target: 'allure-results'
                    )

                    // Generate report
                    allure includeProperties: false,
                            results: [[path: 'target/allure-results']]
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