package net.softler.config

import com.typesafe.config.{Config, ConfigFactory}

/**
  * The root server configuration
  */
trait ServerConfig {

  lazy val config: Config = ConfigFactory.load()
  lazy val serverConfig: Config = config.getConfig("http")
  private lazy val dbConfig = config.getConfig("database")

  lazy val host: String = serverConfig.getString("interface")
  lazy val port: Int = serverConfig.getInt("port")

  lazy val jdbcUrl: String = dbConfig.getString("url")
  lazy val dbUser: String = dbConfig.getString("user")
  lazy val dbPassword: String = dbConfig.getString("password")
  lazy val dbDriver: String = dbConfig.getString("driver")
}
