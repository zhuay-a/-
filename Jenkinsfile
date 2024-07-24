pipeline{
  agent any

    stages{
      stage('Build'){
        steps{
            TCA_Builder(codeAnalysisPath: '/zhuay/CodeAnalysis/', teamId: 'A15aEovCEcC', projectName: 'demo', token: '0712b895f30c5e958ec71a7c22e1b1a2ad1d5c6b', branchName: 'master', languageType: 'Java', refSchemeID: '1', scanPlan: 'model', threshold: '90', total: true)
            script{
                def tca_status = readFile('tca_threshold.txt')
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
