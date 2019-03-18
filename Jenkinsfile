pipeline {
    agent any

    tools{
        maven 'local_Maven'
    }

    parameters{
        string(name: 'tomcat_dev', defaultValue: '13.55.1.23', description: 'Staging Server')
        string(name: 'tomcat_prod', defaultValue: '54.206.114.44', description: 'Production Server')
    }

    triggers {
         pollSCM('* * * * *')
     }

    stages{
        stage('Build'){
            steps {
                sh 'mvn clean package'
            }
            post {
                success {
                    echo 'Archiving...'
                    archiveArtifacts artifacts: '**/target/*.war'
                }
            }
        }

    stage ('Deployments'){
            parallel{
                stage ('Deploy to Staging'){
                    steps {
                        sh "scp -o StrictHostKeyChecking=no -i /Users/ennoh/tomcat-demo.pem /Users/Shared/Jenkins/Home/workspace/blog/target/*.war ec2-user@${params.tomcat_dev}:/var/lib/tomcat8/webapps"
                    }
                }

                stage ('Deploy to Production'){
                    steps {
                        sh "scp -o StrictHostKeyChecking=no -i /Users/ennoh/tomcat-demo.pem /Users/Shared/Jenkins/Home/workspace/blog/target/*.war ec2-user@${params.tomcat_prod}:/var/lib/tomcat8/webapps"
                    }
                }


            }
        }
    }
}

