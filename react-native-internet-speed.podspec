require "json"

package = JSON.parse(File.read(File.join(__dir__, "package.json")))

Pod::Spec.new do |s|
  s.name           = 'react-native-internet-speed'
  s.version        = package['version']
  s.summary        = package['description']
  s.description    = package['description']
  s.author         = package['author']
  s.homepage       = 'https://github.com/sstatsuya/react-native-internet-speed'
  s.source       = { :git => "https://github.com/sstatsuya/react-native-internet-speed.git", :tag => "master" }

  s.ios.deployment_target = "8.0"

  s.subspec "RNInternetSpeed" do |ss|
    ss.source_files  = "ios/*.{h,m}"
    s.static_framework = true
  end

  s.dependency "React"
end
