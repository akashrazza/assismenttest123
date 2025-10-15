resource "aws_s3_bucket" "static_website_bucket" {
  bucket = "my-static-website-bucket"

  website {
    index_document = "index.html"
    error_document = "error.html"
  }
}

resource "aws_s3_bucket_acl" "static_website_bucket_acl" {
  bucket = aws_s3_bucket.static_website_bucket.id
  acl    = "public-read"
}

resource "aws_s3_bucket_policy" "bucket_policy" {
  bucket = aws_s3_bucket.static_website_bucket.id
  policy = jsonencode({
    Version = "2012-10-17",
    Statement = [
      {
        Sid       = "PublicReadGetObject",
        Effect    = "Allow",
        Principal = "*",
        Action    = "s3:GetObject",
        Resource  = "${aws_s3_bucket.static_website_bucket.arn}/*"
      }
    ]
  })
}

resource "local_file" "website_files" {
  for_each = fileset("./public/", "**/*")
  content  = file("./public/${each.value}")
  filename = "./temp_upload/${each.value}" # Temporary location for files before uploading

  provisioner "local-exec" {
    command = "aws s3 cp ./temp_upload/${each.value} s3://${aws_s3_bucket.static_website_bucket.id}/${each.value} --endpoint-url=http://localhost:4566"
  }
}

output "website_endpoint" {
  value = "http://${aws_s3_bucket.static_website_bucket.id}.s3-website.localhost.localstack.cloud:4566"
}