pipeline {
    agent any
    tools{
        maven 'local_Maven'
    }
    stages{
        stage('Build'){
            steps {
		echo 'Building..'
                sh 'mvn clean package'
            }
            post {
                success {
                    echo 'Archiving...'
                    archiveArtifacts artifacts: '**/target/*.war'
                }
            }
        }
	    stage('Test') {
            steps {
                echo 'Testing..'
            }
        }
        stage('Deploy to staging') {
            steps {
                echo 'Deploying to staging....'
                build job: 'blog_deploy_to_staging'
            }
        }
        stage ('Deploy to Production'){
            steps{
                timeout(time:5, unit:'DAYS'){
                    input message:'Deploy it to a production environment?'
                }

                build job: 'blog_deploy_to_production'
            }
            post {
                success {
                    echo 'The project has been successfully deployed to the production environment.'
                }

                failure {
                    echo 'Deployment failed.'
                }
            }
        }

    }
}