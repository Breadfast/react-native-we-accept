require 'json'

package = JSON.parse(File.read(File.join(__dir__, '../package.json')))
Pod::Spec.new do |s|
  s.version        = package['version']
  s.summary        = package['description']
  s.description    = package['description']
  s.license        = package['license']
  s.author         = package['author']
  s.homepage       = package['homepage']
  s.name         = "RNWeAccept"
  # s.license      = { :type => "MIT", :file => "FILE_LICENSE" }
  s.platform     = :ios, "7.0"
  s.source       = { :git => "https://github.com/Breadfast/react-native-we-accept.git", :tag => "master" }
  s.source_files  = "RNWeAccept/**/*.{h,m}"
  s.requires_arc = true


  s.dependency "React"
  s.dependency 'AcceptCardSDK'
  #s.dependency "others"

end

  