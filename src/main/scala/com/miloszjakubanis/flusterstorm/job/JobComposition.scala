package com.miloszjakubanis.flusterstorm.job

import scala.concurrent.Future

class JobComposition[A, C, B](
    val left: Job[A, C],
    val right: Job[C, B]
) extends Job[A, B] {


//  def apply(): Future[B] = ???

  def apply(a: A): Future[B] =
    for {
      b <- fit(left, a)
      c <- fit(right, b)
    } yield c

  private def fit[A, B](job: Job[A, B], a: A): Future[B] = job match {
    case f: Function0[Future[B]]    => f.apply()
    case f: Function1[A, Future[B]] => f.apply(a)
  }
}