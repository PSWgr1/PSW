using System;
using System.ComponentModel.DataAnnotations;

namespace Web.Entities
{
    public class Content
    {
        [Key]
        public int Id { get; set; }
        public string Value { get; set; } = String.Empty;
        public string Description { get; set; } = String.Empty;
        public DateTime Modified { get; set; }
        public File Parent { get;  set; }
    }
}