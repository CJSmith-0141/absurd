package net.tazato.surreal.ql

enum Keywords(val value: String):
  case INFO      extends Keywords("INFO")
  case FOR       extends Keywords("FOR")
  case KV        extends Keywords("KV")
  case NS        extends Keywords("NS")
  case NAMESPACE extends Keywords("NAMESPACE")
  case DB        extends Keywords("DB")
  case DATABASE  extends Keywords("DATABASE")
  case SCOPE     extends Keywords("SCOPE")
  case TABLE     extends Keywords("TABLE")
