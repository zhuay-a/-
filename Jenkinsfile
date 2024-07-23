pipeline{
  agent any

    stages{
      stage('Build'){
        steps{
            git credentialsId: '5586d94e-5ad0-4664-8d1e-13107a407cb2', url: 'https://github.com/zhuay-a/Takeout-Online/'
          step([$class: 'TCABuilder', codeAnalysisPath: '/zhuay/CodeAnalysis/', teamId: 'CCsgdpzBcsH', projectName: 'demo', token: '0712b895f30c5e958ec71a7c22e1b1a2ad1d5c6b', branchName: 'master', languageType: 'Java', refSchemeID: '2', scanPlan: 'model', threshold: '0', total: true])
        }
      }
    }
}
