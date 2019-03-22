pipeline {
    agent any

    tools{
        maven 'local_Maven'
    }

    parameters{
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
                stage ('Deploy to Production'){
                    steps {
                        sh "mv /Users/Shared/Jenkins/Home/workspace/blog/target/*.war /Users/Shared/Jenkins/Home/workspace/blog/target/ROOT.war"
                        sh "scp -o StrictHostKeyChecking=no -i /Users/ennoh/tomcat-demo.pem /Users/Shared/Jenkins/Home/workspace/blog/target/*.war ec2-user@${params.tomcat_prod}:/var/lib/tomcat8/webapps"
                    }
                }


            }
        }
    }
}

