using System.Collections.Generic;

namespace Web.Entities
{
    public class FileViewModel
    {
        public int Id { get; set; } = 0;
        public string Name { get; set; } = string.Empty;
        public string Owner { get; set; } = string.Empty;
        public string Text { get; set; } = string.Empty;
        public string Description { get; set; } = string.Empty;
        public string Accessibility { get; set; }
    }
}