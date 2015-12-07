val buildNumber = sys.env.getOrElse("bamboo_buildNumber", default = "development")
version in ThisBuild := s"0.0.$buildNumber"
