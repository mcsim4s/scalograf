package scalograf

import client.GrafanaConfig.{LoginPassword, Scheme}
import client.api.folders.{CreateFolderRequest, UpdateFolderRequest}
import client.{GrafanaClient, GrafanaConfig}

import com.dimafeng.testcontainers.ContainerDef
import com.dimafeng.testcontainers.scalatest.TestContainerForAll
import org.scalatest.OptionValues
import org.scalatest.matchers.should
import org.scalatest.wordspec.AsyncWordSpec
import sttp.client3.ResponseException
import sttp.client3.asynchttpclient.future.AsyncHttpClientFutureBackend

import java.io.File
import scala.concurrent.Future
import scala.util.Random

class FoldersSpec extends AsyncWordSpec with should.Matchers with OptionValues with TestContainerForAll {
  val testDataDir = new File(getClass.getResource("/testDashboards").getPath)
  override val containerDef: ContainerDef = GrafanaContainer.Def()

  "Grafana client" should {
    s"found folders after creating" in withContainers { case container: GrafanaContainer =>
      println(container.url)
      val client = GrafanaClient(
        GrafanaConfig(
          Scheme("http", container.host, container.port),
          LoginPassword("admin", "admin")
        ),
        AsyncHttpClientFutureBackend()
      )

      for {
        _ <- client.createFolder(CreateFolderRequest("first", "title"))
        _ <- client.createFolder(CreateFolderRequest("second", "title1"))
        folders <- client.listFolders()
        res <- folders.body match {
          case Right(listOfFolders) => {
            assert(listOfFolders.nonEmpty)
            assert(
              Set("first", "second").forall(uid =>
                listOfFolders
                  .map(f => f.uid)
                  .toSet
                  .contains(uid)
              )
            )
          }
          case _ => assert(false)
        }
      } yield res
    }

    s"found by uid" in withContainers { case container: GrafanaContainer =>
      println(container.url)
      val client = GrafanaClient(
        GrafanaConfig(
          Scheme("http", container.host, container.port),
          LoginPassword("admin", "admin")
        ),
        AsyncHttpClientFutureBackend()
      )

      for {
        created <- client.createFolder(CreateFolderRequest("getByUid", "getByUid"))
        folder <- eitherToFuture(created.body)
        getByUid <- client.getByUid(folder.uid)
        res <- getByUid.body match {
          case Right(f) => assert(f.uid == "getByUid")
          case Left(_) => assert(false)
        }
      } yield res

    }

    s"found by id" in withContainers { case container: GrafanaContainer =>
      println(container.url)
      val client = GrafanaClient(
        GrafanaConfig(
          Scheme("http", container.host, container.port),
          LoginPassword("admin", "admin")
        ),
        AsyncHttpClientFutureBackend()
      )

      for {
        created <- client.createFolder(CreateFolderRequest("getById", "getById"))
        folder <- eitherToFuture(created.body)
        getById <- client.getById(folder.id)
        res <- getById.body match {
          case Right(f) => assert(folder.uid == "getById")
          case Left(some) => assert(false)
        }
      } yield res
    }

    s"update folder" in withContainers { case container: GrafanaContainer =>
      println(container.url)
      val client = GrafanaClient(
        GrafanaConfig(
          Scheme("http", container.host, container.port),
          LoginPassword("admin", "admin")
        ),
        AsyncHttpClientFutureBackend()
      )

      val folderUid = Random.nextLong().toString
      val folderTitle = Random.nextString(5)

      for {
        created <- client.createFolder(CreateFolderRequest(folderUid, folderTitle))
        folder <- eitherToFuture(created.body)
        _ <- client.updateFolder(folderUid, UpdateFolderRequest(s"new_uid_${folder.uid}", s"new $folderTitle"))
        all <- client.listFolders()
        res <- all.body match {
          case Right(listOfFolders) => {
            assert(listOfFolders.nonEmpty)
            assert(listOfFolders.map(_.uid).contains(s"new_uid_$folderUid"))
          }
          case _ => assert(false)
        }
      } yield res
    }

    s"delete folder" in withContainers { case container: GrafanaContainer =>
      println(container.url)
      val client = GrafanaClient(
        GrafanaConfig(
          Scheme("http", container.host, container.port),
          LoginPassword("admin", "admin")
        ),
        AsyncHttpClientFutureBackend()
      )

      val folderUid = "uid"
      val folderTitle = Random.nextString(5)

      client
        .createFolder(CreateFolderRequest(folderUid, folderTitle))
        .flatMap(_ => client.deleteFolder(folderUid))
        .flatMap(_ => client.listFolders())
        .map(_.body)
        .map {
          case Right(listOfFolders) => assert(!listOfFolders.exists(f => f.uid == folderUid))
          case _ => assert(false)
        }
    }
  }

  private def eitherToFuture[T](e: Either[ResponseException[_, _], T]): Future[T] =
    e match {
      case Right(value) => Future.successful(value)
      case Left(ex) => Future.failed(ex)
    }
}
