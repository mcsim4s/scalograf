package scalograf
package syntax

import model.alert.condition.query._

import scalograf.model.time.Time

import scala.concurrent.duration.FiniteDuration
import scala.language.implicitConversions

trait AlertSyntax {
  case class TimeRange(from: Time, window: FiniteDuration)

  sealed trait Condition {
    def and(condition: Condition): Condition = And(this, condition)
    def or(condition: Condition): Condition = Or(this, condition)
  }

  case class And(left: Condition, right: Condition) extends Condition
  case class Or(left: Condition, right: Condition) extends Condition
  case class Complete(reducerType: ReducerType, label: String, range: TimeRange, evaluator: Evaluator) extends Condition

  def renderQuery(condition: Condition): List[QueryCondition] = {
    condition match {
      case And(left, right) =>
        renderQuery(left) ++ renderQuery(right).mapFirst(_.copy(operator = Operator(OperationType.And)))
      case Or(left, right) =>
        renderQuery(left) ++ renderQuery(right).mapFirst(_.copy(operator = Operator(OperationType.Or)))
      case Complete(reducerType, label, range, evaluator) =>
        List(
          QueryCondition(
            evaluator = evaluator,
            query = Query(label, range.from, range.window),
            operator = Operator(OperationType.And),
            reducer = Reducer(`type` = reducerType, params = List.empty)
          )
        )
    }
  }

  case class RL(reducerType: ReducerType, label: String) {
    def over(window: FiniteDuration) = RLW(reducerType, label, window)
  }

  case class RLW(reducerType: ReducerType, label: String, window: FiniteDuration) {
    def from(from: Time) = RLR(reducerType, label, TimeRange(from, window))
  }

  case class RLR(reducerType: ReducerType, label: String, range: TimeRange) {
    def isAbove(value: Double): Complete =
      Complete(reducerType, label, range, Evaluator(EvaluationType.IsAbove, List(value)))

    def isBelow(value: Double): Complete =
      Complete(reducerType, label, range, Evaluator(EvaluationType.IsBelow, List(value)))

    def withinRange(from: Double, to: Double): Complete =
      Complete(reducerType, label, range, Evaluator(EvaluationType.IsWithinRange, List(from, to)))

    def outsideRange(from: Double, to: Double): Complete =
      Complete(reducerType, label, range, Evaluator(EvaluationType.IsOutsideRange, List(from, to)))

    def hasNoValue(): Complete =
      Complete(reducerType, label, range, Evaluator(EvaluationType.HasNoValue, List()))
  }

  def avg(label: String): RL = RL(ReducerType.Average, label)
  def count(label: String): RL = RL(ReducerType.Count, label)
  def countNotNull(label: String): RL = RL(ReducerType.CountNotNull, label)
  def diff(label: String): RL = RL(ReducerType.Diff, label)
  def diffAbs(label: String): RL = RL(ReducerType.DiffAbs, label)
  def last(label: String): RL = RL(ReducerType.Last, label)
  def max(label: String): RL = RL(ReducerType.Max, label)
  def median(label: String): RL = RL(ReducerType.Median, label)
  def min(label: String): RL = RL(ReducerType.Min, label)
  def percentDiff(label: String): RL = RL(ReducerType.PercentDiff, label)
  def percentDiffAbs(label: String): RL = RL(ReducerType.PercentDiffAbs, label)
  def sum(label: String): RL = RL(ReducerType.Sum, label)

  implicit class RichList[A](val list: List[A]) {
    def mapFirst[B](fun: A => B): List[B] = {
      list.foldLeft[List[B]](List.empty) {
        case (acc, elem) =>
          acc match {
            case ::(head, _) => acc :+ head
            case Nil         => fun(elem) :: Nil
          }
      }
    }
  }

  implicit def condition2model(condition: Condition): List[QueryCondition] = renderQuery(condition)
}
