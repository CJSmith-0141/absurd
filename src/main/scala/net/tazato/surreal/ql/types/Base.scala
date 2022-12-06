package net.tazato.surreal.ql.types

enum Base(val value: String):
  case DATABASE  extends Base("DATABASE")
  case DB        extends Base("DB")
  case NAMESPACE extends Base("NAMESPACE")
  case NS        extends Base("NS")
