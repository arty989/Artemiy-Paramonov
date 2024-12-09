SELECT post.post_id FROM post
LEFT JOIN comment ON comment.post_id = post.post_id
GROUP BY post.post_id
HAVING COUNT(comment.post_id) IN (0, 1)
ORDER BY post.post_id ASC
LIMIT 10