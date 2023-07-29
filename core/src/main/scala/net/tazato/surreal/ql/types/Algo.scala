/*
 * Copyright 2023 Connor James Smith
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package net.tazato.surreal.ql.types

import cats.syntax.all.*

/** Simple enum, stringified reps of surrealdb supported encryption algorithms.
  */
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
