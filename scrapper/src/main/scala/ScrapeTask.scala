package scalograf

import model.CommunityDashboardId

case class ScrapeTask(id: CommunityDashboardId, name: String, scrapedWith: Option[String])
