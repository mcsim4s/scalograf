package scalograf

import model.CommunityDashboardId

case class SchemeError(dashboardId: CommunityDashboardId, scrapedWith: String, `type`: String, message: String)
