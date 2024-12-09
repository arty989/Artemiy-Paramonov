SELECT comment.post_id FROM comment
GROUP BY comment.post_id
HAVING COUNT(comment.post_id) IN (0, 1)
ORDER BY comment.post_id ASC
LIMIT 10