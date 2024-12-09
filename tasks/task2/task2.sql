SELECT comment.post_id FROM comment
INNER JOIN post ON post.post_id = comment.post_id
WHERE LEFT(post.title, 1) ~ '^[0-9]' AND LENGTH(post.content) > 20
GROUP BY comment.post_id
HAVING COUNT(comment.post_id) = 2
ORDER BY comment.post_id ASC