package com.sagesurfer.school.home

data class YouthResponseModel(
    val Mood: List<Mood>,
    val Recent_updates: List<RecentUpdate>,
    val daily_planner: List<DailyPlanner>,
    val gratitude_journal: List<GratitudeJournal>,
    val msg: String,
    val status: Int
)

data class Mood(
    val id: String,
    val mood_color: String,
    val mood_name: String,
    val mood_url: String,
    val total_mood: String
)

data class RecentUpdate(
    val added_by: String,
    val description: String,
    val group_id: Int,
    val group_name: String,
    val id: Int,
    val is_read: String,
    val module: String,
    val profile: String,
    val status: Int,
    val sub_type: String,
    val timestamp: Int,
    val type: String
)

data class DailyPlanner(
    val added_by: String,
    val c_date: String,
    val color: String,
    val date: Int,
    val desc: String,
    val description: String,
    val end_date: String,
    val event_time: String,
    val frequency: String,
    val frequency_sub_unit_days: String,
    val frequency_unit: String,
    val full_name: String,
    val goal_current_status: Int,
    val goal_posting_date: String,
    val goal_status: Int,
    val goal_type: String,
    val id: String,
    val image: String,
    val image_id: String,
    val impact: String,
    val is_dashboard: String,
    val is_delete: Int,
    val is_owner: Int,
    val is_read: Int,
    val last_updated: Int,
    val location: String,
    val name: String,
    val notify: Int,
    val notify_at: String,
    val notify_frequency: String,
    val occurrences: String,
    val own_or_team: String,
    val participants: List<partici_pants>,
    val prio: String,
    val priority: Int,
    val progress: Int,
    val quantity: Int,
    val start_date: String,
    val start_time: String,
    val status: Int,
    val team_id: String,
    val team_name: String,
    val text: String,
    val thumb: String,
    val timestamp: Int,
    val title: String,
    val todo_status: String,
    val total_pagination_number: Int,
    val type: String,
    val units: String,
    val username: String,
    val task_owner_profile: String
)

data class partici_pants(
    val name: String,
    val photo: String,
    val role: String,
)

data class GratitudeJournal(
    val attachments: List<Attachments>,
    val db_add_date: Int,
    val db_date: String,
    val description: String,
    val id: String,
    val is_fav: String,
    val latitude: String,
    val link: String,
    val longitude: String,
    val status: Int,
    val subject: String,
    val tags: String,
    val title: String
) {
    data class Attachments(
        val id: String,
        val path: String,
        val image: String
    )
}