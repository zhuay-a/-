pipeline{
  agent any

    stages{
      stage('Build'){
        steps{
            step([$class: 'TCABuilder', codeAnalysisPath: '/zhuay/CodeAnalysis/', teamId: 'CCsgdpzBcsH', projectName: 'demo', token: '0712b895f30c5e958ec71a7c22e1b1a2ad1d5c6b', branchName: 'master', languageType: 'Java', refSchemeID: '2', scanPlan: 'model', threshold: '100', total: true])
            script{
                def tca_status = sh(script: 'cat tca_threshold.txt', returnStdout: true)
                if (tca_status == "success") {
                    echo ">> tca scan pass!"
                } else {
                    echo ">> tca scan fail! exit code 255"
                    error("TCA scan failed. Terminating pipeline.")
                }
            }
        }
      }
    }
}
