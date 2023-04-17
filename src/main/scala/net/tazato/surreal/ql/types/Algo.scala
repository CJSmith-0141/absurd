package net.tazato.surreal.ql.types

import cats.implicits.*

enum Algo(val value: String):
  case EdDSA extends Algo("EDDSA")
  case Es256 extends Algo("ES256")
  case Es384 extends Algo("ES384")
  case Es512 extends Algo("ES512")
  case Hs256 extends Algo("HS256")
  case Hs384 extends Algo("HS384")
  case Hs512 extends Algo("HS512")
  case Ps256 extends Algo("PS256")
  case Ps384 extends Algo("PS384")
  case Ps512 extends Algo("PS512")
  case Rs256 extends Algo("RS256")
  case Rs384 extends Algo("RS384")
  case Rs512 extends Algo("RS512")
