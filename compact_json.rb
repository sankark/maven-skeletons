require 'rubygems'
require 'json'
File.open('compact.json','w') { |f|
  f.write JSON.unparse( JSON.parse( File.read('images_cloudformation.json')) )
}

