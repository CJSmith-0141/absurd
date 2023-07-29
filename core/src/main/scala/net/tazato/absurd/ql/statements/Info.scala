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

package net.tazato.absurd.ql.statements

import net.tazato.absurd.ql.*
import net.tazato.absurd.ql.types.*

enum Info(val info: String, val ident: Option[String]):
  case KV               extends Info("KV", None)
  case NS               extends Info("NS", None)
  case NAMESPACE        extends Info("NAMESPACE", None)
  case DB               extends Info("DB", None)
  case DATABASE         extends Info("DATABASE", None)
  case SCOPE(x: String) extends Info("SCOPE", Some(x))
  case TABLE(x: String) extends Info("TABLE", Some(x))
  lazy val render: String = Info.render(this)

object Info extends Render[Info]:
  import Info.*
  override def render(x: Info): String =
    x match
      case KV | NS | NAMESPACE | DB | DATABASE =>
        s"INFO FOR ${x.info};"
      case Info.SCOPE(_) | Info.TABLE(_) =>
        s"INFO FOR ${x.info} ${x.ident.get};"
