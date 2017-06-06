# springboot-test-exclude-config
Shows problem with excluding configuration

If you start the application from the MainConfig class, you will successfully start it.
 
 If you start the test CompanyTest, the startup will fail because both DB configuration are started even if one is set to be excluded
